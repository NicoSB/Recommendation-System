package ch.uzh.ifi.seal.ase.cin;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.declarations.MethodDeclaration;
import com.google.common.collect.Lists;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SSTTestUtils {

    public static SST createSSTWithMethod(IMethodName methodName, IStatement... methodBody) {
        MethodDeclaration md = new MethodDeclaration();
        if(methodName != null)
            md.setName(methodName);
        md.setBody(Lists.newArrayList(methodBody));

        SST sst = new SST();
        sst.getMethods().add(md);

        return sst;
    }
}
