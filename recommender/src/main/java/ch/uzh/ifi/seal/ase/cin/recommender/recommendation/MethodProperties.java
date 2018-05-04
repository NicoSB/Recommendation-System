package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.MethodKind;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Visibility;

import java.util.Objects;

public class MethodProperties {
    private String methodName;
    private Visibility visibility;
    private MethodKind methodKind;

    public MethodProperties(IMethodName methodName) {
        this.methodName = methodName.getFullName();
        // TODO extract visibility
        this.visibility = Visibility.PUBLIC;
        this.methodKind = methodName.isStatic() ? MethodKind.CLASS : MethodKind.INSTANCE;
    }

    public String getMethodName() {
        return methodName;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public MethodKind getMethodKind() {
        return methodKind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodProperties that = (MethodProperties) o;
        return Objects.equals(methodName, that.methodName) &&
                visibility == that.visibility &&
                methodKind == that.methodKind;
    }

    @Override
    public int hashCode() {

        return Objects.hash(methodName, visibility, methodKind);
    }
}
