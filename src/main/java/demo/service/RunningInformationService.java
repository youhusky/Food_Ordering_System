package demo.service;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RunningInformationService {

    List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformationList);

    void deleteAll();
    void deleteByRunningId(String runningId);


    Page<RunningInformation> findAll(Pageable pageable);

    Page<RunningInformation> findByRunningId(String runningId, Pageable pageable);

    Page<RunningInformation> findByHeartRateGreaterThan(int heartRate, Pageable pageable);

    Page<RunningInformation> findByTotalRunningTimeGreaterThan(double totalRunningTime, Pageable pageable);

    /*Test Feature*/

    Page<RunningInformation> findByUserInfo_Zipcode(int zipcode, Pageable pageable);

    Page<RunningInformation> findByUserInfo_State(String zipcode, Pageable pageable);
}
