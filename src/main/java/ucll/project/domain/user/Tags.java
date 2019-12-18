package ucll.project.domain.user;

import java.util.ArrayList;

public enum Tags {

    TAG1("Integrity"),
    TAG2("Curiosity"),
    TAG3("Collaboration"),
    TAG4("Client first"),
    TAG5("Entrepreneurship"),
    TAG6("Move Faster"),
    TAG7("Act smarter"),
    TAG8("Go further"),
    TAG9("Be sure"),
    TAG10("Team spirit"),
    TAG11("Office spirit");

    private String tag;

    Tags(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }


}
