package com.revature.zuul.filters;

import com.netflix.zuul.ZuulFilter;

public class ErrorFilter extends ZuulFilter{

	@Override
	public Object run() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "error";
	}

}
