package com.dastres.master.comparator;

import java.util.Comparator;

import com.dastres.master.domain.SlaveTO;

public class RecordCountOrderComparator implements Comparator<SlaveTO> {

	private boolean asc;

	public RecordCountOrderComparator(boolean asc) {
		this.asc = asc;
	}

	@Override
	public int compare(SlaveTO o1, SlaveTO o2) {
		if (asc) {
			return myCompare(o1, o2);
		} else {
			return myCompare(o2, o1);
		}

	}

	private int myCompare(SlaveTO o1, SlaveTO o2) {
		return o1.getRecordSize() - o2.getRecordSize();
	}

}
