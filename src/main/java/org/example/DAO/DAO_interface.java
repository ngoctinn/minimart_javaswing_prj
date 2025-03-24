package org.example.DAO;

import org.example.DTO.khachHangDTO;

import java.util.List;

public interface DAO_interface<T> {
    List<T> getAllList();
    boolean addData(T entity);
    boolean updateData(T entity);
    boolean deleteData(String id);
}
