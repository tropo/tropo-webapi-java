package support;

import java.lang.reflect.Constructor;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.TropoException;
import com.voxeo.tropo.actions.Action;
import com.voxeo.tropo.actions.ArrayBackedJsonAction;
import com.voxeo.tropo.actions.JsonAction;
	
public class ActionSupportHandler<E extends Action> {
	
	private Class<E> clazz;

	public ActionSupportHandler(Class<E> clazz) {
		
		this.clazz = clazz;
	}

	public E build(Action action, Key... keys) throws TropoException {

		return build(action, null, keys);
	}
	
	public E build(Action action, String name, Key... keys) throws TropoException {

		try {
			Constructor<E> c = clazz.getConstructor(Key[].class);
			E instanceAction = c.newInstance(new Object[]{keys});
			if (action instanceof ArrayBackedJsonAction) {
				action.addToArray(name, instanceAction.getName(), instanceAction);
			} else if (action instanceof JsonAction) {
				action.put(instanceAction.getName(),instanceAction);
			} else {
				action.put(instanceAction.getName(),instanceAction);
			}
			return instanceAction;
		} catch (TropoException te) {
			throw te;
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause() instanceof TropoException) {
				throw (TropoException)e.getCause();
			}
			e.printStackTrace();
		}
		return null;
	}
}
