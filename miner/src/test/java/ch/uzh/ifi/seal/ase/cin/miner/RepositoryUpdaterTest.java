package ch.uzh.ifi.seal.ase.cin.miner;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.QuerySelectionPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryUpdaterTest {

    @InjectMocks
    private RepositoryUpdater sut;

    @Mock
    private ModelRepository repository;

    @Test
    public void updatesModelRepository() {
        String typeName = "Test";
        MethodName methodName = new MethodName("Test");
        QuerySelectionPair pair = new QuerySelectionPair(new Query(typeName), methodName);

        sut.insertPair(pair);

        verify(repository, times(1)).save(argThat(r -> r.getType().equals(typeName)));
    }

    @Test
    public void whenQueryIsNull_doesNotUpdateModelRepository() {
        String typeName = "Test";
        MethodName methodName = new MethodName("Test");
        QuerySelectionPair pair = new QuerySelectionPair(null, methodName);

        sut.insertPair(pair);

        verify(repository, times(0)).save(argThat(r -> r.getType().equals(typeName)));
    }

    @Test
    public void whenSelectionIsNull_doesNotUpdateModelRepository() {
        String test = "Test";
        QuerySelectionPair pair = new QuerySelectionPair(new Query(test), null);

        sut.insertPair(pair);

        verify(repository, times(0)).save(argThat(r -> r.getType().equals(test)));
    }

}