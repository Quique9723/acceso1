package com.core.acceso1.tools;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;

@Getter
@Setter
@ToString
public class FormGenericPage<T> implements Serializable{
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private FormGenericPiece<T> formGenericPiece = new FormGenericPiece<T>();
	
	private List <Integer> pageSizeList;
	private Integer pageSizeSelected;
	private Integer numPartials;
	
	private Integer totalItems = 0;
	private Integer totalPages = 0;
	private Integer currentPage = 0;
	private Integer partialPagePrev = 0;
	private Integer partialPageNext = 0;
	
	private Integer pageNumber1;
	private Integer pageNumber2;
	private Integer pageNumber3;
	
	private Integer totalPageGroups;
	private Integer currentPageGroupNumber;
	private Integer numberOfPagesOfLastPageGroup;
	
	private Boolean isActiveFirstPage;
	private Boolean isActivePrevPage;
	private Boolean isActiveNextPage;
	private Boolean isActiveLastPage;
	private Boolean isVisiblePage2;
	private Boolean isVisiblePage3;
	
	public FormGenericPage(List<Integer> pageSizeList,
			Integer pageSizeSelected,
			Integer numPartials) {
		
		super();
		this.pageSizeList = pageSizeList;
		this.pageSizeSelected = pageSizeSelected;
		this.numPartials = numPartials;
		
		
	}
	public void changeFirstGroupPages() {
		this.currentPageGroupNumber = 1;
		this.changeCurrentPage(1);
	}
	
	public void changeCurrentPage(Integer pageNum) {
		this.setCurrentPage(pageNum);
		this.buildPaginator();
	}
	
	public void buildPaginator() {
		this.isActiveFirstPage = (this.currentPageGroupNumber > 1) ? true : false;
		this.isActiveLastPage = (this.currentPageGroupNumber < totalPageGroups) ? true : false;
		
		this.isActivePrevPage = (this.currentPageGroupNumber > 1) ? true : false;
		this.isActiveNextPage = (this.currentPageGroupNumber < totalPageGroups) ? true : false;
		
		this.pageNumber1 = (this.currentPageGroupNumber * this.pageSizeSelected)
				-(this.pageSizeSelected - 1);
		
		if((this.currentPageGroupNumber < this.totalPageGroups)||
				((this.currentPageGroupNumber == this.totalPageGroups)
						&& (this.numberOfPagesOfLastPageGroup == 3)) ) {
			
			this.pageNumber2 = this.pageNumber1 + 1;
			this.pageNumber3 = this.pageNumber2 + 1;
			this.isVisiblePage2 = true;
			this.isVisiblePage3 = true;

			
		}
		else {
			if(this.numberOfPagesOfLastPageGroup==2) {
				this.pageNumber2 = this.pageNumber1 +1;
				this.isVisiblePage2 = true;
				this.pageNumber3 = this.pageNumber2;
				this.isVisiblePage3=false;
				
			}
			else {
				this.pageNumber2 = this.pageNumber1;
				this.isVisiblePage2 = false;
				this.pageNumber3=this.pageNumber1;
				this.isVisiblePage3=false;
			}
		}
		
			
		
	}
	public void changeLastGroupPages() {
		this.currentPageGroupNumber = this.totalPageGroups;
		this.changeCurrentPage(this.totalPages);
		
	}
	
	public void changeCurrentPageSize(int pageSizeSelected) {
		this.pageSizeSelected = pageSizeSelected;
		this.totalPages = (this.totalItems % this.pageSizeSelected==0)
				?(this.totalItems / this.pageSizeSelected)
				: 1 + (this.totalItems / this.pageSizeSelected);
		this.totalPageGroups= (this.totalPages / this.pageSizeSelected)
				+((this.totalPages % this.pageSizeSelected==0) ? 0 : 1);
		
		this.numberOfPagesOfLastPageGroup = this.totalPages % this.pageSizeSelected;
		this.currentPageGroupNumber = 1;
		this.changeCurrentPage(1);
	}
	public void changePrevGroupPages() {
		this.currentPageGroupNumber = (this.currentPageGroupNumber > 1) ? (this.currentPageGroupNumber - 1)
				:1;
		this.changeCurrentPage((this.currentPageGroupNumber * this.pageSizeSelected) -2);
		
	}
	
	public void changeNextGroupPages() {
		this.currentPageGroupNumber = (this.currentPageGroupNumber >= this.totalPageGroups)
				? this.currentPageGroupNumber
				: 1 + this.currentPageGroupNumber;
		this.changeCurrentPage((this.currentPageGroupNumber * this.pageSizeSelected)-2);
		
	}
	public void setRequestMappingClassString(String string) {
		// TODO Auto-generated method stub
		
	}
	public Pageable calculatePageRequest(int i, Integer pageSizeSelected2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
