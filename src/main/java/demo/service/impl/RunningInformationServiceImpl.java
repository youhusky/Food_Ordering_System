package demo.service.impl;


import demo.domain.RunningInformation;
import demo.domain.RunningInformationRepository;
import demo.service.RunningInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningInformationServiceImpl implements RunningInformationService {



    private RunningInformationRepository runningInformationRepository;


    @Autowired
    public RunningInformationServiceImpl(RunningInformationRepository runningInformationRepository) {

        this.runningInformationRepository = runningInformationRepository;
    }

    /*Create*/
    @Override
    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformationList) {
        return runningInformationRepository.save(runningInformationList);
    }

    /*Delete*/
    @Override
    public void deleteByRunningId(String runningId) {
        runningInformationRepository.deleteByRunningId(runningId);
    }
    @Override
    public void deleteAll() {
        runningInformationRepository.deleteAll();
    }

    /*Query All*/
    @Override
    public Page<RunningInformation> findAll(Pageable pageable) {
        return runningInformationRepository.findAll(pageable);
    }

    /*Query For Each*/
    @Override
    public Page<RunningInformation> findByRunningId(String runningId, Pageable pageable) {
        return runningInformationRepository.findByRunningId(runningId, pageable);
    }

    @Override
    public Page<RunningInformation> findByHeartRateGreaterThan(int heartRate, Pageable pageable) {
        return runningInformationRepository.findByHeartRateGreaterThan(heartRate, pageable);
    }

    @Override
    public Page<RunningInformation> findByTotalRunningTimeGreaterThan(double totalRunningTime, Pageable pageable) {
        return runningInformationRepository.findByTotalRunningTimeGreaterThan(totalRunningTime, pageable);
    }

    /*Test Feature*/
    @Override
    public Page<RunningInformation> findByUserInfo_Zipcode(int zipcode, Pageable pageable) {
        return runningInformationRepository.findByUserInfo_Zipcode(zipcode, pageable);
    }

    @Override
    public Page<RunningInformation> findByUserInfo_State(String state, Pageable pageable) {
        return runningInformationRepository.findByUserInfo_State(state, pageable);
    }


}
