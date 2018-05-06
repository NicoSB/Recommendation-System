package ch.uzh.ifi.seal.ase.cin.recommender.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Arrays;
import java.util.Objects;

public class Query {
    @JsonIgnore
    private String type;
    private String[] expectedTypes = new String[] {};
    private EnclosingNodeKind enclosingNodeKind = EnclosingNodeKind.DEFAULT;
    private CompletionNodeKind completionNodeKind = CompletionNodeKind.DEFAULT;
    private MethodKind enclosingMethodKind = MethodKind.NONE;
    private Visibility enclosingMethodVisibility = Visibility.NONE;

    public Query(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getExpectedTypes() {
        return expectedTypes;
    }

    public void setExpectedTypes(String[] expectedTypes) {
        this.expectedTypes = expectedTypes;
    }

    public EnclosingNodeKind getEnclosingNodeKind() {
        return enclosingNodeKind;
    }

    public void setEnclosingNodeKind(EnclosingNodeKind enclosingNodeKind) {
        this.enclosingNodeKind = enclosingNodeKind;
    }

    public CompletionNodeKind getCompletionNodeKind() {
        return completionNodeKind;
    }

    public void setCompletionNodeKind(CompletionNodeKind completionNodeKind) {
        this.completionNodeKind = completionNodeKind;
    }

    public MethodKind getEnclosingMethodKind() {
        return enclosingMethodKind;
    }

    public void setEnclosingMethodKind(MethodKind enclosingMethodKind) {
        this.enclosingMethodKind = enclosingMethodKind;
    }

    public Visibility getEnclosingMethodVisibility() {
        return enclosingMethodVisibility;
    }

    public void setEnclosingMethodVisibility(Visibility enclosingMethodVisibility) {
        this.enclosingMethodVisibility = enclosingMethodVisibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(type, query.type) &&
                Arrays.equals(expectedTypes, query.expectedTypes) &&
                enclosingNodeKind == query.enclosingNodeKind &&
                completionNodeKind == query.completionNodeKind &&
                enclosingMethodKind == query.enclosingMethodKind &&
                enclosingMethodVisibility == query.enclosingMethodVisibility;
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(type, enclosingNodeKind, completionNodeKind, enclosingMethodKind, enclosingMethodVisibility);
        result = 31 * result + Arrays.hashCode(expectedTypes);
        return result;
    }

    @Override
    public String toString() {
        return "Query{" +
                "type='" + type + '\'' +
                ", expectedTypes=" + Arrays.toString(expectedTypes) +
                ", enclosingNodeKind=" + enclosingNodeKind +
                ", completionNodeKind=" + completionNodeKind +
                ", enclosingMethodKind=" + enclosingMethodKind +
                ", enclosingMethodVisibility=" + enclosingMethodVisibility +
                '}';
    }
}