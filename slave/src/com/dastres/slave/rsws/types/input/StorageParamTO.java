package com.dastres.slave.rsws.types.input;

import java.util.List;


public class StorageParamTO extends ParamTO {
	private List<LocationStorageParamTO> locStorageTOs;

	public void setLocStorageTOs(List<LocationStorageParamTO> locStorageTOs) {
		this.locStorageTOs = locStorageTOs;
	}

	public List<LocationStorageParamTO> getLocStorageTOs() {
		return locStorageTOs;
	}



	
}
