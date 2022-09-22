package com.company.controller;

import com.company.dto.SubscriptionDTO;
import com.company.dto.SubscriptionInfoDTO;
import com.company.entity.SubscriptionEntity;
import com.company.mapper.SubscriptionInfo;
import com.company.service.SubscriptionService;
import com.company.util.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Subscription")
@Slf4j
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @ApiOperation(value = "Subscription create", notes="Method for Subscription create")
    @PostMapping("/user/create")
    public ResponseEntity<?> create(@RequestBody @Valid SubscriptionDTO dto) {
        log.info("Request for create {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        SubscriptionDTO articleDTO = subscriptionService.create(dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body(articleDTO);
    }

    @ApiOperation(value = "Subscription update", notes="Method for Subscription update")
    @PutMapping("/user/changeStatus/{channelId}")
    public ResponseEntity<?> changeStatus(@PathVariable("channelId") String channelId, @RequestBody SubscriptionDTO dto) {
        log.info("Request for update {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        SubscriptionEntity channel = subscriptionService.changeStatus(channelId, dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }

    @ApiOperation(value = "Subscription update", notes="Method for Subscription update")
    @PutMapping("/user/changeNotification/{channelId}")
    public ResponseEntity<?> changeNotification(@PathVariable("channelId") String channelId, @RequestBody SubscriptionDTO dto) {
        log.info("Request for update {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        SubscriptionEntity channel = subscriptionService.changeNotification(channelId, dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }

    @ApiOperation(value = "list By Subscription", notes="Method for list By Subscription")
    @GetMapping("/public/subscription")
    public ResponseEntity<?> listById() {
        log.info("Request for listByCategory {}" );
        List<SubscriptionInfoDTO> list = subscriptionService.listSubscription();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "list By ProfileId", notes="Method for  list By ProfileId")
    @GetMapping("/adm/{profileId}")
    public ResponseEntity<?> listByProfileId(@PathVariable("profileId") Integer profileId) {
        log.info("Request for list By ProfileId {}"  );
        List<SubscriptionInfoDTO> list = subscriptionService.listByProfileId(profileId);
        return ResponseEntity.ok().body(list);
    }
}
