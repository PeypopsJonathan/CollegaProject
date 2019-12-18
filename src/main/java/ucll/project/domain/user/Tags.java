package ucll.project.domain.user;

import java.util.ArrayList;

public enum Tags {

    TAG1("What a great worker."),
    TAG2("Awesome stuff."),
    TAG3("Have my children."),
    TAG4("Happy with your service.");

    private String tag;

    Tags(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }


}
