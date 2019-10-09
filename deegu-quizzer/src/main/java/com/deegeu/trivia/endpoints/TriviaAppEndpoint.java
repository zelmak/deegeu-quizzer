package com.deegeu.trivia.endpoints;

import com.deegeu.trivia.model.TriviaQuestion;
import com.deegeu.trivia.model.TriviaQuestionAccessible;
import com.deegeu.trivia.model.TriviaQuestionArrayAccess;
import java.util.Date;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/questions")
public class TriviaAppEndpoint {

    private final TriviaQuestionAccessible dataAccess = new TriviaQuestionArrayAccess();
    private static final int STARTING_OFFSET = 0;
    private static final int PAGE_SIZE = 10;
    private final Date questionsUpdatedDate = new Date();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestions(@Context UriInfo uri, @QueryParam("offset") @DefaultValue("0") long offset) {

        // calculate offset based on the number of items in the "database"
        long datasetSize = dataAccess.getQuestionListSize();
        long start = offset;

        if (start < STARTING_OFFSET) {
            start = STARTING_OFFSET;
        }

        if (start >= datasetSize) {
            start = datasetSize;
        }

        // setup navigation links
        Link selfLink = Link.fromUri(uri.getBaseUri() + "question?offset={offset}")
                .rel("self").type(MediaType.APPLICATION_JSON)
                .build(offset);

        long nextOffset = (offset + PAGE_SIZE < datasetSize)
                ? offset + PAGE_SIZE : PAGE_SIZE * (datasetSize / PAGE_SIZE);
        Link nextLink = Link.fromUri(uri.getBaseUri() + "questions?offset={offset}")
                .rel("next").type(MediaType.APPLICATION_JSON)
                .build(nextOffset);

        long prevOffset = (offset - PAGE_SIZE > STARTING_OFFSET) ? offset - PAGE_SIZE : STARTING_OFFSET;
        Link prevLink = Link.fromUri(uri.getBaseUri() + "questions?offset={offset}")
                .rel("prev").type(MediaType.APPLICATION_JSON)
                .build(prevOffset);

        Link firstLink = Link.fromUri(uri.getBaseUri() + "questions?offset={offset}")
                .rel("first").type(MediaType.APPLICATION_JSON)
                .build(STARTING_OFFSET);

        Link lastLink = Link.fromUri(uri.getBaseUri() + "questions?offset={offset}")
                .rel("last").type(MediaType.APPLICATION_JSON)
                .build(PAGE_SIZE * (datasetSize / PAGE_SIZE));

        Link countLink = Link.fromUri(uri.getBaseUri() + "questions/count")
                .rel("count").type(MediaType.APPLICATION_JSON)
                .build();

        Link rndLink = Link.fromUri(uri.getBaseUri() + "questions/random")
                .rel("random").type(MediaType.APPLICATION_JSON)
                .build();

        // build response       
        List<TriviaQuestion> list = dataAccess.getQuestionList(start);
        return Response.ok()
                .header("question-count", datasetSize)
                .header("current-question-list-size", list.size())
                .header("offset", start)
                .links(selfLink, nextLink, prevLink, firstLink, lastLink, countLink, rndLink)
                .lastModified(new Date())
                .location(uri.getRequestUri())
                .build();

    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionCount(@Context UriInfo uri) {
        long numberOfQuestions = dataAccess.getQuestionListSize();
        return Response.ok()
                .header("question-count", numberOfQuestions)
                .lastModified(new Date())
                .location(uri.getRequestUri())
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getquestion(@Context UriInfo uri, @PathParam("id") String idString) {
        Response response;

        if (idString.trim().equalsIgnoreCase("random")) {
            TriviaQuestion question = dataAccess.getRandomQuestion();
            response = Response.ok(question)
                    .lastModified(question.getLastUpdated())
                    .location(uri.getRequestUri())
                    .build();
        } else {
            try {
                long identifier = Long.parseLong(idString);
                if (identifier >= dataAccess.getQuestionListSize()) {
                    response = Response.status(Response.Status.NOT_FOUND)
                            .build();
                } else {
                    TriviaQuestion question = dataAccess.getQuestionById(identifier);
                    response = Response.ok(question)
                            .lastModified(question.getLastUpdated())
                            .location(uri.getRequestUri())
                            .build();
                }
            } catch (NumberFormatException e) {
                response = Response.status(Response.Status.BAD_REQUEST).build();
            }
        }

        return response;
    }

}
