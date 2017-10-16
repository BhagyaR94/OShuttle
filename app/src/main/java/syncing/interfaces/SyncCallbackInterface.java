package syncing.interfaces;

import java.util.ArrayList;

import syncing.model.SyncList;

/**
 * Created by Chathun on 7/1/2016.
 */
public interface SyncCallbackInterface {

  void liveDownloadCount(ArrayList<SyncList> resObjects);
}
