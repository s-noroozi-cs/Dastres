package test.dastres.master;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.dastres.master.comparator.RecordCountOrderComparator;
import com.dastres.master.domain.SlaveTO;

public class StorageStatisticTest extends TestCase {

	public final void testCoutOrderComparator() {
		List<SlaveTO> list = new ArrayList<SlaveTO>();
		String asc = "";
		String desc = "";
		for (int i = 0; i < 10; i++) {
			SlaveTO ss = new SlaveTO();
			ss.setRecordSize(i);
			list.add(ss);
			asc += i;
			desc = i + desc;
		}

		String testOrder = "";
		Collections.sort(list, new RecordCountOrderComparator(true));
		for (Iterator<SlaveTO> itr = list.iterator(); itr.hasNext();) {
			testOrder += itr.next().getRecordSize();
		}
		assertTrue(asc.equals(testOrder));

		testOrder = "";
		Collections.sort(list, new RecordCountOrderComparator(false));
		for (Iterator<SlaveTO> itr = list.iterator(); itr.hasNext();) {
			testOrder += itr.next().getRecordSize();
		}
		assertTrue(desc.equals(testOrder));
	}

}
