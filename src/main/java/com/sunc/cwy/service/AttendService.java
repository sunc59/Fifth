package com.sunc.cwy.service;

import com.sunc.cwy.mapper.AttendMapper;
import com.sunc.cwy.model.Attend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunc
 */
@Service
public class AttendService {

    @Autowired(required = false)
    private AttendMapper attendMapper;

    public int listAttendsCount(Attend attend) {
        return 0;
    }

    public List<Attend> listAttends(Attend attend) {
        return null;
    }

    public List<Attend> listAttendTemps(Attend attend) {
        return null;
    }

    public Attend getAttend(Attend attend) {
        return null;
    }

    public void updateAttendType(Attend attend) {
    }

    public void addAttendBatch() {
    }

    public void updateAttendTypeBatchLeave() {
    }

    public void updateAttendTypeBatchPost() {
    }

    public void addAttendBatch(String date, int i) {
    }

    public void updateAttendTypeBatchLeave(String date) {
    }

    public void updateAttendTypeBatchPost(String date) {
    }

    public void addAttendBatch(String date, int user_id, int i) {
    }

    public void updateAttendTypeBatchLeave(String date, int user_id) {
    }

    public void updateAttendTypeBatchPost(String date, int user_id) {
    }

    public void updateAttendTypeBatchUser(Attend attend) {
    }

    public void delAttends(String[] split) {
    }
}
