package com.applaudo.assignement.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class ScheduleItem {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss Z");

	public static final SimpleDateFormat DAY_FORMATTER = new SimpleDateFormat(
			"EEEE M/d");

	public static final SimpleDateFormat HOUR_FORMATTER = new SimpleDateFormat(
			"h:mma");

	@SerializedName("start_date")
	private Date mStartDate;
	@SerializedName("end_date")
	private Date mEndDate;

	public ScheduleItem() {

	}

	public ScheduleItem(Date startDate, Date endDate) {
		mStartDate = startDate;
		mEndDate = endDate;
	}

	public ScheduleItem(String startDate, String endDate) throws ParseException {
		mStartDate = FORMATTER.parse(startDate);
		mEndDate = FORMATTER.parse(endDate);
	}

	public Date getStartDate() {
		return mStartDate;
	}

	public void setStartDate(Date startDate) {
		mStartDate = startDate;
	}

	public Date getEndDate() {
		return mEndDate;
	}

	public void setEndDate(Date endDate) {
		mEndDate = endDate;
	}

	public void setStartDate(String startDate) throws ParseException {
		mStartDate = FORMATTER.parse(startDate);
	}

	public void setEndDate(String endDate) throws ParseException {
		mEndDate = FORMATTER.parse(endDate);
	}

	public String getStartDateString() {
		return FORMATTER.format(mStartDate);
	}

	public String getEndDateString() {
		return FORMATTER.format(mEndDate);
	}

	public String getScheduleString() {
		return DAY_FORMATTER.format(mStartDate) + " "
				+ HOUR_FORMATTER.format(mStartDate) + " to "
				+ HOUR_FORMATTER.format(mEndDate);
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof ScheduleItem) {
			result = mStartDate.equals(((ScheduleItem) o).getStartDate())
					&& mEndDate.equals(((ScheduleItem) o).getEndDate());
		}
		return result;
	}

	@Override
	public int hashCode() {
		String s = getStartDateString() + getEndDateString();
		return s.hashCode();
	}

}