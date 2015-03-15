package com.samenea.commons.webmvc.model;

public class BaseGrid {

	private int totalPages;
	private int currentPage;
	private long totalRecords;

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords, long pageSize) {

		this.totalRecords = totalRecords;
		this.totalPages = (int) Math.ceil((float) totalRecords / pageSize);
	}
}