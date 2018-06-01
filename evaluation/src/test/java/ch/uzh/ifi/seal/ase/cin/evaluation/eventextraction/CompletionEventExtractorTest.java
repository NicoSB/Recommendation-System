package ch.uzh.ifi.seal.ase.cin.evaluation.eventextraction;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.utils.io.IReadingArchive;
import ch.uzh.ifi.seal.ase.cin.evaluation.eventextraction.CompletionEventExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompletionEventExtractorTest {

    @Mock
    private IReadingArchive archive;

    @Test
    public void whenArchiveDoesNotContainAnyCompletionEvents_returnsEmptyList() {
        IIDEEvent mockEvent = mock(IIDEEvent.class);
        Consumer mockConsumer = mock(Consumer.class);
        when(archive.hasNext()).thenReturn(true).thenReturn(false);
        when(archive.getNext(IIDEEvent.class)).thenReturn(mockEvent);

        CompletionEventExtractor.extractAndConsumeCompletionEvents(archive, mockConsumer);

        verify(mockConsumer, Mockito.times(0)).accept(any());
    }


    @Test
    public void extractsAllCompletionEvents_returnsEmptyList() {
        Consumer mockConsumer = mock(Consumer.class);

        IIDEEvent mockEvent = mock(IIDEEvent.class);
        IIDEEvent mockCompletionEvent = mock(ICompletionEvent.class);
        when(archive.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(archive.getNext(IIDEEvent.class)).thenReturn(mockEvent).thenReturn(mockCompletionEvent);

        CompletionEventExtractor.extractAndConsumeCompletionEvents(archive, mockConsumer);

        verify(mockConsumer, Mockito.times(1)).accept(any());
    }

}