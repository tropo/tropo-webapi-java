package com.voxeo.tropo.actions;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import support.ActionSupportHandler;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.RequiredKeys;
import com.voxeo.tropo.annotations.ValidKeys;

/**
 * <p>
 * Its allows multiple lines in separate sessions to be conferenced together so
 * that the parties on each line can talk to each other simultaneously. This is
 * a voice channel only feature.
 * </p>
 * <p>
 * Any conference ID you use in production will be shared among all your
 * production applications; likewise any development app conference IDs are
 * shared among all your development apps. These IDs are not shared with other
 * accounts, so if you have conference ID 1234, you won't conflict if another
 * Tropo user is also using 1234.
 * </p>
 * <p>
 * Each conference can hold up to 100 people at one time.
 * </p>
 */
@ValidKeys(keys={"name","allowSignals","mute","on","playTones","terminator","id","send_tones","exit_tone","interdigitTimeout","joinPrompt","leavePrompt","required","promptLogSecurity"})
@RequiredKeys(keys={"id","name"})
public class ConferenceAction extends JsonAction {

	private ActionSupportHandler<NestedOnAction> onActionSupportHandler = new ActionSupportHandler<NestedOnAction>(NestedOnAction.class);	

	public ConferenceAction() {
		
		super();
		setName("conference");
	}
	
	public ConferenceAction(Key... keys) {
	
		super(keys);
		setName("conference");
	}
	
	public NestedOnAction on(Key... keys) {

		return onActionSupportHandler.build(this, keys);
	}
	
	public NestedOnAction on(String event, String next) {

		return on(EVENT(event), NEXT(next));
	}
}
