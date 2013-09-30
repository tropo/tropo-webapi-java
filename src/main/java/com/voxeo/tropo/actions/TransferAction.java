package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.VALUE;
import support.ActionSupportHandler;
import support.HeadersSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={"to","allowSignals","choices","from","headers","name","on","required","ringRepeat","timeout","answerOnMedia","interdigitTimeout"})
@RequiredKeys(keys={"to"})
public class TransferAction extends JsonAction {

	private ActionSupportHandler<NestedOnAction> onRecordingActionSupportHandler = new ActionSupportHandler<NestedOnAction>(NestedOnAction.class);
	private ActionSupportHandler<ChoicesAction> choicesRecordingActionSupportHandler = new ActionSupportHandler<ChoicesAction>(ChoicesAction.class);
	private HeadersSupportHandler headersSupportHandler = new HeadersSupportHandler();

	public TransferAction() {

		super();
		setName("transfer");
	}

	public TransferAction(Key... keys) {

		super(keys);
		setName("transfer");
	}

	public NestedOnAction on(Key... keys) {

		return onRecordingActionSupportHandler.build(this, keys);
	}

	public NestedOnAction on(String event, String next) {

		return on(EVENT(event), NEXT(next));
	}

	public ChoicesAction choices(Key... keys) {

		return choicesRecordingActionSupportHandler.build(this, keys);
	}

	public AskAction choices(String choice) {

		return (AskAction)and(choices(VALUE(choice)));
	}

	public HeadersAction headers(String[]... keys) {

		return headersSupportHandler.headers(this, keys);
	}
}
