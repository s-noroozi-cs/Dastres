package com.dastres.util;

import java.util.List;

import com.dastres.domain.EntityTO;

public class CollectionUtil {

	public static boolean isNotEmpty(List<? extends EntityTO> list) {
		return list != null && list.size() > 0;
	}
	
	public static boolean isEmpty(List<? extends EntityTO> list) {
		return !isNotEmpty(list);
	}

}
