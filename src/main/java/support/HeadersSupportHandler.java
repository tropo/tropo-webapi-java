package support;

import com.voxeo.tropo.actions.Action;
import com.voxeo.tropo.actions.HeadersAction;
	
public class HeadersSupportHandler {
	
	public HeadersAction headers(Action action, String[]... keys) {
		
		HeadersAction headersAction = new HeadersAction(keys);
		action.put("headers",headersAction);
		
		return headersAction;
	}
}
