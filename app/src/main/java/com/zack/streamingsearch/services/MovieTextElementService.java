package com.zack.streamingsearch.services;


import com.zack.streamingsearch.openmoviedb.models.OpenMovieRequestModel;

public class MovieTextElementService {


    public static String ingestMovieDataForDisplay(OpenMovieRequestModel openMovieRequestModel) {
        String titleTag = "Title: " + openMovieRequestModel.getTitle(),
                yearTag = "Year: " + openMovieRequestModel.getYear(),
                releasedTag = "Released: " + openMovieRequestModel.getReleased(),
                runtimeTag =  "Runtime: " + openMovieRequestModel.getRuntime(),
                directorTag = "Director(s): " + openMovieRequestModel.getDirector(),
                writerTag = "Writer(s): " + openMovieRequestModel.getWriter(),
                actorsTag = "Actors: " + openMovieRequestModel.getActors(),
                awardsTag = "Awards: " + openMovieRequestModel.getAwards(),
                boxOfficeTag = "BoxOffice: " + openMovieRequestModel.getBoxOffice(),
                plotTag = "Plot: " + openMovieRequestModel.getPlot(),
                resultString = "";
                resultString += titleTag + System.lineSeparator() + System.lineSeparator() +
                        yearTag + System.lineSeparator() + System.lineSeparator() +
                            releasedTag + System.lineSeparator() +  System.lineSeparator() +
                                runtimeTag + System.lineSeparator() + System.lineSeparator() +
                                    directorTag + System.lineSeparator() + System.lineSeparator() +
                                        writerTag + System.lineSeparator() +  System.lineSeparator() +
                                            actorsTag + System.lineSeparator() + System.lineSeparator() +
                                                awardsTag + System.lineSeparator() +  System.lineSeparator() +
                                                    boxOfficeTag + System.lineSeparator() + System.lineSeparator() +
                                                        plotTag;

        return resultString;
    }

    public String getNewLine() {
        return System.lineSeparator();
    }
}
