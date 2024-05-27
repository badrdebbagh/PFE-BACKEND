package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.CasTest;
import com.backend.backend_pfe.model.TestCaseDescription;
import com.backend.backend_pfe.repository.CasTestRepository;
import com.backend.backend_pfe.repository.TestCaseDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TestCaseDescriptionServiceImpl implements TestCaseDescriptionService{

    private final TestCaseDescriptionRepository repository;
    private final CasTestRepository casTestRepository;

    @Autowired
    public TestCaseDescriptionServiceImpl(TestCaseDescriptionRepository repository, CasTestRepository casTestRepository) {
        this.repository = repository;
        this.casTestRepository = casTestRepository;
    }

    @Override
    public List<TestCaseDescription> findDescriptionByCasTestId(Long casTestId) {
        return repository.findByCasTestId(casTestId);
    }

    @Override
    public TestCaseDescription saveDescriptionForTestCase(TestCaseDescription description, Long casTestId) {
        CasTest casTest = casTestRepository.findById(casTestId)
                .orElseThrow(() -> new RuntimeException("Test Case not found with ID: " + casTestId));
        description.setCasTest(casTest);


        return repository.save(description);
    }
}
