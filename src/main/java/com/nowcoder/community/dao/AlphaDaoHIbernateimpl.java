package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")
public class AlphaDaoHIbernateimpl implements AlphaDao {

    @Override
    public String select() {
        return "hell dao";
    }
}
