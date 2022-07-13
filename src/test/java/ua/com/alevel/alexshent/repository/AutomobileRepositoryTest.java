package ua.com.alevel.alexshent.repository;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.service.AutomobileService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileRepositoryTest {

    @Test
    void create() {
        // given
        int numberOfAutos = 5;
        int wantedNumberOfInvocations = 1;
        // when
        AutomobileService service = new AutomobileService();
        List<Automobile> autos = service.createAutos(numberOfAutos);
        String targetAutoId = autos.get(0).getId();
        AutomobileRepository repository = Mockito.mock(AutomobileRepository.class);
        repository.create(autos);
        Mockito.when(repository.create(service.createAutos(numberOfAutos))).thenReturn(true);
        // then
        Mockito.verify(repository, Mockito.times(wantedNumberOfInvocations)).create(Mockito.anyList());
        Mockito.verify(repository, Mockito.never()).update(Mockito.any());
        Mockito.verify(repository).create(Mockito.argThat(
                (ArgumentMatcher<List<Automobile>>) list -> list != null && !list.isEmpty()
        ));
        repository.getById(targetAutoId);
        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(repository).getById(idArgumentCaptor.capture());
        String capturedId = idArgumentCaptor.getValue();
        assertEquals(capturedId, targetAutoId);
        Mockito.verifyNoMoreInteractions(repository);
    }
}