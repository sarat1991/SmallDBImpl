package com.db;



public class DBRequest {

	private String query;
	private String[] outputColumns;
	private String conditionColumn;
	private String conditionOperator;
	private String conditionValue;
	private String orderByColumn;
	private int queryLength;
	private boolean validRequestFormat;
	
	
	
	public int getQueryLength() {
		return queryLength;
	}

	public void setQueryLength(int queryLength) {
		this.queryLength = queryLength;
	}

	
	public boolean isValidRequestFormat() {
		return validRequestFormat;
	}

	public void setValidRequestFormat(boolean validRequestFormat) {
		this.validRequestFormat = validRequestFormat;
	}

	public String getQuery() {
		return query;
	}

	public String[] getOutputColumns() {
		return outputColumns;
	}

	public String getConditionColumn() {
		return conditionColumn;
	}

	public String getConditionOperator() {
		return conditionOperator;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public String getOrderBy() {
		return orderByColumn;
	}
	

	

	public DBRequest(String query){
		super();
		this.query = query;
		processRequest();
	}
	
	private void processRequest() {
		
		String[] queryTokens = query.split(" ");
		queryLength = queryTokens.length;
		validRequestFormat = queryLength == 9? true: queryLength == 5?true:queryLength == 2? true: queryLength == 6? true:false;
		if(!validRequestFormat)
			return;
		if((queryTokens[0].toLowerCase().equals("select")) && queryLength > 1){
			outputColumns = queryTokens[1].split(",");
			
			
			if((queryLength == 9)||(queryLength == 5)){
				if((query.toLowerCase().matches("(.*)order by(.*)"))){
					if(queryLength == 5)
						orderByColumn = queryTokens[4];
					else
						orderByColumn = queryTokens[8];
				}
				else {
					validRequestFormat =  false;
					return;
				}
			}
			
			if(queryTokens.length >= 6){
				
				if(query.matches("(.*)(<|>|=|!=)(.*)")){
					conditionColumn=queryTokens[3];
					conditionOperator = queryTokens[4];
					conditionValue=queryTokens[5];
					}
			    else{
			    	validRequestFormat =  false;
			    	return;
				}
			}
			validRequestFormat = true; 
			
		}
		else{
			validRequestFormat =  false;
			
			}		
	}
	
}



