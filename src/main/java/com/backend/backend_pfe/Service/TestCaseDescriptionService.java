package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.TestCaseDescription;

import java.util.List;
import java.util.Optional;

public interface TestCaseDescriptionService {
   

    TestCaseDescription saveDescriptionForTestCase(TestCaseDescription description, Long casTestId);

    List<TestCaseDescription> findDescriptionByCasTestId(Long testCaseId);
}
