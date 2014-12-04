package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"milliseconds"})
@RequiredKeys(keys={"milliseconds"})
public class WaitAction extends JsonAction {

    public WaitAction() {
        super();
        setName("wait");
    }

    public WaitAction(Key... keys) {
        super(keys);
        setName("wait");
    }
}
