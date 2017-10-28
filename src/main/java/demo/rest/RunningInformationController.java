package demo.rest;


import com.alibaba.fastjson.JSONObject;
import demo.domain.RunningInformation;
import demo.service.RunningInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RunningInformationController {


    private final String defaultRequirePage = "0";
    private final String defaultRequireSize = "2";

    private final String defaultListAllPage = "0";
    private final String defaultListAllSize = "20";


    @Autowired
    private RunningInformationService runningInformationService;

    /*Create*/
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RunningInformation> runningInformationList){
        runningInformationService.saveRunningInformation(runningInformationList);
    }

    /*Delete*/
    @Transactional
    @RequestMapping(value = "/delete/{runningId}", method = RequestMethod.DELETE)
    public void deleteByRunningId(@PathVariable String runningId) {

        runningInformationService.deleteByRunningId(runningId);
    }

    @Transactional
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public void deleteAll(){
        runningInformationService.deleteAll();
    }


    /*Query All*/
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = defaultRequirePage) int page,
            @RequestParam(name = "size", defaultValue = defaultRequireSize) int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"heartRate");
        List<RunningInformation> content = runningInformationService.findAll( new PageRequest(page, size, sort)).getContent();
        return new ResponseEntity<List<JSONObject>>(getJsonResult(content), HttpStatus.OK);
    }

    /*Query For Each*/
    @RequestMapping(value = "/findId/{runningId}", method = RequestMethod.GET)
    public ResponseEntity<?> findByRunningId(@PathVariable String runningId,
                                                @RequestParam(name = "page",defaultValue = defaultRequirePage) int page,
                                                @RequestParam(name = "size",defaultValue = defaultRequireSize) int size){
        List<RunningInformation> content = runningInformationService.findByRunningId(runningId,new PageRequest(page,size)).getContent();
        return new ResponseEntity<List<JSONObject>>(getJsonResult(content),HttpStatus.OK);
    }

    @RequestMapping(value = "/findByHeartRateGreaterThan/{heartRate}", method = RequestMethod.GET)
    public ResponseEntity<?> findByHeartRateGreaterThan(@PathVariable int heartRate,
                                                        @RequestParam(name = "page",defaultValue = defaultListAllPage) int page,
                                                        @RequestParam(name = "size",defaultValue = defaultListAllSize) int size){
        Sort order = new Sort(Sort.Direction.DESC,"heartRate");
        List<RunningInformation> content = runningInformationService.findByHeartRateGreaterThan( heartRate,
                new PageRequest(page, size, order)).getContent();
        return new ResponseEntity<List<JSONObject>>(getJsonResult(content), HttpStatus.OK);

    }

    @RequestMapping(value = "/findByTotalRunningTimeGreaterThan/{totalRunningTime}", method = RequestMethod.GET)
    public ResponseEntity<?> findByTotalRunningTimeGreaterThan(@PathVariable double totalRunningTime,
                                                               @RequestParam(name = "page",defaultValue = defaultListAllPage) int page,
                                                               @RequestParam(name = "size",defaultValue = defaultListAllSize) int size)
    {
        Sort order = new Sort(Sort.Direction.DESC, "totalRunningTime");
        List<RunningInformation> content = runningInformationService.findByTotalRunningTimeGreaterThan(totalRunningTime,
                new PageRequest(page,size,order)).getContent();
        return new ResponseEntity<List<JSONObject>>(getJsonResult(content),HttpStatus.OK);
    }

    /*Test Feature*/
    @RequestMapping(value = "/findByZipcode/{zipcode}", method = RequestMethod.GET)
    public ResponseEntity<?> findByUserInfo_Zipcode(@PathVariable int zipcode,
                                                    @RequestParam(name = "page",defaultValue = defaultListAllPage) int page,
                                                    @RequestParam(name = "size",defaultValue = defaultListAllSize) int size) {

        List<RunningInformation> content = runningInformationService.findByUserInfo_Zipcode(zipcode,
                new PageRequest(page,size)).getContent();
        return new ResponseEntity<List<JSONObject>>(getJsonResult(content),HttpStatus.OK);
    }

    @RequestMapping(value = "/findByState/{state}", method = RequestMethod.GET)
    public ResponseEntity<?> findByUserInfo_State(@PathVariable String state,
                                                  @RequestParam(name = "page",defaultValue = defaultListAllPage) int page,
                                                  @RequestParam(name = "size",defaultValue = defaultListAllSize) int size) {
        List<RunningInformation> content = runningInformationService.findByUserInfo_State(state,
                new PageRequest(page,size)).getContent();
        return new ResponseEntity<List<JSONObject>>(getJsonResult(content),HttpStatus.OK);
    }

    //JSON Object
    private JSONObject getOneJsonResult(RunningInformation item){
        JSONObject info = new JSONObject(true);
        info.put("runningId", item.getRunningId());
        info.put("totalRunningTime", item.getTotalRunningTime());
        info.put("heartRate", item.getHeartRate());
        info.put("userId", item.getId());
        info.put("userName", item.getUserInfo().getUsername());
        info.put("userAddress", item.getUserInfo().getAddress());
        info.put("healthWarningLevel", item.getHealthWarningLevel());
        return info;
    }
    private List<JSONObject> getJsonResult(List<RunningInformation> runningInformationList){
        List<JSONObject> results = new ArrayList<JSONObject>();
        for (RunningInformation item : runningInformationList) {
            JSONObject info = getOneJsonResult(item);
            results.add(info);
        }
        return results;
    }
}
