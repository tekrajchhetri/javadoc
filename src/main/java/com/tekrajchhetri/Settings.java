package com.tekrajchhetri;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.PathConverter;

import java.nio.file.Path;


@Parameters(separators = "=")
public class Settings {

    @Parameter(names = {"-h", "--help"},
            help = true,
            description = "Help Information: can be separated by space and equal sign. Eg: -n 5 or -n=5 is same")
    private boolean help;

    @Parameter(names = {"-f", "--file"},
//            arity = 1,
            required = true,
            validateWith = FilePathValidator.class,
            converter = PathConverter.class,
            description = "Absolute file path - required"

    )
    private Path fpath ;//= Paths.get("C:\\\\Users\\\\tekraj\\\\Music\\\\parallel programming\\\\FileManipulate\\\\src/timing.log");

    @Parameter(names = {"-n","--number"},
//            arity = 1,
            description = "Number to display N records - required, default 10 ",
            required = true)
    private Integer numbertoDisplay = 10;


    public boolean isHelp() {
        return help;
    }

    public Path  getpath(){
        return fpath;
    }

    public int getNumber(){
        return numbertoDisplay;
    }
}
