package com.project.rest.webservices.restfuwebservices.filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		//MappingJacksonValue
		SomeBean someBean = new SomeBean("something here","and here","here as well");
		List<SomeBean> list = new ArrayList<>();
		list.add(someBean);
		Set<String> set = new HashSet<>();
		set.add("field2");
		String filterName = "someFilter";
		
		return filteringLogic(list, set, filterName);
		
		
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List<SomeBean> list =  Arrays.asList( new SomeBean("something here","and here","here as well"),
				new SomeBean("value1","value2","value3"));
		
		Set<String> set = new HashSet<>();
		set.add("field2");
		set.add("field3");
		String filterName = "someFilter";
		
		return filteringLogic(list, set, filterName);
	}
	
	public MappingJacksonValue filteringLogic(List<SomeBean> bean, Set<String> values, String filterName) {
		MappingJacksonValue mapping = new MappingJacksonValue(bean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(values);
		FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter );
		mapping.setFilters(filters);
		
		return mapping;
	}

}
