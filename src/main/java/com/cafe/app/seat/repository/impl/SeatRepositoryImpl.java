package com.cafe.app.seat.repository.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.app.seat.repository.SeatRepository;
import com.cafe.app.seat.vo.RequestTempVO;
import com.cafe.app.seat.vo.SeatVO;

@Repository
public class SeatRepositoryImpl extends SqlSessionDaoSupport implements SeatRepository{

	private final String NAME_SPACE = "com.cafe.app.seat.repository.impl.SeatRepositoryImpl.";
	@Autowired // 안 붙이면 에러 발생
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
	  
	@Override
	public List<SeatVO> selectAllSeats() {
		return super.getSqlSession().selectList(this.NAME_SPACE + "selectAllSeats");
	}

	@Override
	public int insertTempSeat(RequestTempVO requestTempVO) {
		return super.getSqlSession().insert(this.NAME_SPACE + "insertTempSeat", requestTempVO);
	}

	@Override
	public int updateSeatStatus(Map<String, String> param) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateSeatStatus", param);
	}

	@Override
	public SeatVO readSeatStatus(String seatId) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectSeatStatus", seatId);
	}

	@Override
	public List<String> selectExpiredSeats() {
		return super.getSqlSession().selectList(this.NAME_SPACE + "selectExpiredSeats");
	}

	@Override
	public int updateReservationStatus(String seatId) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateReservationStatus", seatId);
	}


}
