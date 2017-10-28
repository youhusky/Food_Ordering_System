package demo.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface RunningInformationRepository extends JpaRepository<RunningInformation, Long>{
    //List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformationList);
    Page<RunningInformation> findAll(Pageable pageable);
    void deleteByRunningId(String runningId);

    Page<RunningInformation> findByRunningId(@Param("runningId") String runningId, Pageable pageable);

    Page<RunningInformation> findByHeartRateGreaterThan(@Param("heartRate") int heartRate, Pageable pageable);

    Page<RunningInformation> findByTotalRunningTimeGreaterThan(@Param("totalRunningTime") double totalRunningTime, Pageable pageable);

    Page<RunningInformation> findByUserInfo_Zipcode(@Param("zipcode") int zipcode, Pageable pageable);

    Page<RunningInformation> findByUserInfo_State(@Param("state") String state, Pageable pageable);
}
