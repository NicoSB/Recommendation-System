package ch.uzh.ifi.seal.ase.cin.recommender.recommendation;

import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import ch.uzh.ifi.seal.ase.cin.recommender.model.Query;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.ModelRepository;
import ch.uzh.ifi.seal.ase.cin.recommender.persistence.TypeModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecommendationSystemTest {

    @Mock
    ModelRepository repository;

    @Mock
    MethodRecommender recommender;

    @InjectMocks
    RecommendationSystem sut;

    @Test
    public void WhenSelectionIsProvidedAndModelExists_UpdatesRepository() {
        Query query = new Query("Type");
        String selection = "Selection";

        when(repository.find(query.getType())).thenReturn(new TypeModel(query.getType()));
        sut.updateModel(query, new MethodName(selection));

        verify(repository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void WhenSelectionIsProvidedAndDoesNotModelExist_CreatesNewModel() {
        Query query = new Query("Type");
        String selection = "Selection";

        sut.updateModel(query, new MethodName(selection));

        verify(repository, times(1)).save(argThat(typeModel -> query.getType().equals(typeModel.getType())));
    }

    @Test
    public void WhenRecommendationIsRequested_CallsMethodRecommender() {
        Query query = new Query("Type");

        sut.query(query);

        verify(recommender, times(1)).getRecommendations(argThat(typeModel -> query.getType().equals(typeModel.getType())), ArgumentMatchers.eq(query));
    }
}