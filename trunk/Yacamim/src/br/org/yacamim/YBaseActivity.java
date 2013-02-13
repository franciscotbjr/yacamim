/**
 * YBaseActivity.java
 * 
 * Copyright 2012 yacamim.org.br
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.org.yacamim;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import br.org.yacamim.entity.GpsLocationInfo;
import br.org.yacamim.ui.components.DefaultAlertDialogBuilder;
import br.org.yacamim.util.YConstants;
import br.org.yacamim.util.YUtilUIFields;

/**
 * 
 * Class YBaseActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class YBaseActivity extends Activity implements Callback {
	
	private YBaseLocationListener yBaseLocationListener;
	
	private LocationManager locationManager;
	
	private GpsLocationInfo gpsLocationInfoInicial = new GpsLocationInfo();
	
	private GpsLocationInfo gpsLocationInfoFinal = new GpsLocationInfo();
	
	private StringBuilder message;
	
	private List<ProgressDialog> progressDialogStack = new ArrayList<ProgressDialog>();
	
	/**
	 * 
	 */
	public YBaseActivity() {
		super();
		YacamimState.getInstance().setCurrentActivity(this);
	}
	
	/**
	 *
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.keepScreenOn();
	}

	/**
	 * 
	 */
	protected void initGPS() {
		try {
			this.gpsLocationInfoInicial = new GpsLocationInfo();
			this.gpsLocationInfoFinal = new GpsLocationInfo();
			this.locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			this.yBaseLocationListener = new YBaseLocationListener(this);
			this.locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, yBaseLocationListener);
		} catch (Exception _e) {
			Log.e("YBaseActivity.initGPS", _e.getMessage());
		}
	}

    /**
     * 
     */
	private void keepScreenOn() {
		try {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} catch (Exception _e) {
			Log.e("YBaseActivity.keepScreenOn", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _uiFieldNames
	 * @param _string
	 */
	protected void buildMessage(final List<String> _uiFieldNames, final int _string) {
		try {
			clearMessage();
			this.getMessage().append(getText(_string));
			for(final String nomeCampo : _uiFieldNames) {
				this.getMessage().append("\n");
				this.getMessage().append(" - " + nomeCampo);
			}
			
		} catch (Exception _e) {
			Log.e("YBaseActivity.buildMessage", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _uiFieldNames
	 */
	protected void buildMessage(final List<String> _uiFieldNames) {
		try {
			clearMessage();
			for(final String nomeCampo : _uiFieldNames) {
				this.getMessage().append("\n");
				this.getMessage().append(" - " + nomeCampo);
			}
			
		} catch (Exception _e) {
			Log.e("YBaseActivity._uiFieldNames", _e.getMessage());
		}
	}
	

	/**
	 * 
	 */
	protected void clearMessage() {
		this.message = null;
	}


	/**
	 * 
	 * @return
	 */
	public StringBuilder getMessage() {
		if(this.message == null) {
			this.message = new StringBuilder();
		}
		return message;
	}

	/**
	 * 
	 * @param _message
	 */
	public void displayProgressDialog(final String _message) {
		try {
			this.progressDialogStack.add(ProgressDialog.show(this, "", _message, true, false));
		} catch (Exception _e) {
			Log.e("YBaseActivity.displayProgressDialog", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _mensagem
	 */
	public void displayProgressDialog() {
		try {
			this.progressDialogStack.add(ProgressDialog.show(this, "", this.getText(YacamimResources.getInstance().getIdResourceMsgWait()), true, false));
		} catch (Exception _e) {
			Log.e("YBaseActivity.displayProgressDialog", _e.getMessage());
		}
	}

	/**
	 * 
	 */
	public void removeProgressDialog() {
		try {
			if(this.progressDialogStack != null && !this.progressDialogStack.isEmpty()) {
				this.progressDialogStack.remove(0).cancel();
			}
		} catch (Exception _e) {
			Log.e("YBaseActivity.removeProgressDialog", _e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void clearProgressDialogStack() {
		try {
			if(this.progressDialogStack != null && !this.progressDialogStack.isEmpty()) {
				for(int i = 0; i < this.progressDialogStack.size(); i++) {
					this.progressDialogStack.get(i).cancel();
				}
				this.progressDialogStack.clear();
			}
		} catch (final Exception _e) {
			Log.e("YBaseActivity.clearProgressDialogStack", _e.getMessage());
		}
	}
	
	/**
	 *
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if(YacamimState.getInstance().isLogoff()) {
				this.finish();
				return;
			}
			switch (resultCode) {
				case YConstants.ERROR_NO_INFORMATION_FOUND:
					this.showDialogNoInformationFound();
					break;
				case YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS:
					this.showDialogNoRecordFound();
					break;
			}
		} catch (Exception _e) {
			Log.e("YBaseActivity.onActivityResult", _e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	protected void showDialogNoInformationFound() {
		showDialog(YConstants.ERROR_NO_INFORMATION_FOUND);
	}
	
	/**
	 * 
	 */
	protected void finishForNoInformationFound() {
		super.setResult(YConstants.ERROR_NO_INFORMATION_FOUND);
		super.finish();
	}
	
	/**
	 * 
	 */
	protected void showDialogNoRecordFound() {
		showDialog(YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS);
	}
	
	/**
	 * 
	 */
	protected void showDialogNoRecordSelected() {
		showDialog(YConstants.ERROR_NO_RECORD_SELECTED);
	}
	
	/**
	 * 
	 */
	protected void finishForNoRecordFound() {
		super.setResult(YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS);
		super.finish();
	}
	
	/**
	 * 
	 */
	protected void finishForNoRecordSelected() {
		super.setResult(YConstants.ERROR_NO_RECORD_SELECTED);
		super.finish();
	}

	/**
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(final int _idDialog) {
		switch(_idDialog) {
			case YConstants.ERROR_NO_INFORMATION_FOUND:
				AlertDialog.Builder builderNenhumResultadoEncontrado = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgNoInformationFound()).toString(), false);
	        	builderNenhumResultadoEncontrado.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
	        	           public void onClick(DialogInterface _dialog, int id) {
	        	        	   removeDialog(_idDialog);
	        	           }
	        	       });
	        	return builderNenhumResultadoEncontrado.show();
			case YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS:
	        	AlertDialog.Builder builderNenhumRegistro = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgNoRecordsFoundForParameters()).toString(), false);
	        	builderNenhumRegistro.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
	        	           public void onClick(DialogInterface _dialog, int id) {
	        	        	   removeDialog(_idDialog);
	        	           }
	        	       });
	        	return builderNenhumRegistro.show();
			case YConstants.ERROR_NO_RECORD_SELECTED:
				AlertDialog.Builder builderNenhumRegistroSelecionado = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgNoRecordSelected()).toString(), false);
				builderNenhumRegistroSelecionado.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(_idDialog);
					}
				});
				return builderNenhumRegistroSelecionado.show();
			case YConstants.ERROR_NO_CONNECTIVITY_AVAILABLE:
	        	AlertDialog.Builder builderSemAcessoRede = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgNoConnectivityAvailable()).toString(), false);
	        	builderSemAcessoRede.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
	        	           public void onClick(DialogInterface _dialog, int id) {
	        	        	   removeDialog(_idDialog);
	        	           }
	        	       });
	        	return builderSemAcessoRede.show();
			case YConstants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE:
				AlertDialog.Builder builderSemAcessoWifi = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgNoWifiConnectivityAvailable()).toString(), false);
				builderSemAcessoWifi.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(_idDialog);
					}
				});
				return builderSemAcessoWifi.show();
			case YConstants.INFO_DATA_SUCCESFULLY_INSERTED:
		    	AlertDialog.Builder builderDadosCadastradosSucesso = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgSuccesfullyInserted()), false);
		    	builderDadosCadastradosSucesso.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface _dialog, int id) {
		    			removeDialog(_idDialog);
		    		}
		    	})
		    	;
		    	return builderDadosCadastradosSucesso.show();
			case YConstants.INFO_DATA_SUCCESFULLY_UPDATED:
				AlertDialog.Builder builderDadosAlteradosSucesso = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgSuccesfullyUpdated()), false);
				builderDadosAlteradosSucesso.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(_idDialog);
					}
				})
				;
				return builderDadosAlteradosSucesso.show();
			case YConstants.ERROR_INVALID_DATA:
		    	AlertDialog.Builder builderDadosInvalidos = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgInvalidData()), false);
		    	builderDadosInvalidos.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface _dialog, int id) {
		    			removeDialog(_idDialog);
		    		}
		    	})
		    	;
		    	return builderDadosInvalidos.show();
		    case YConstants.ERROR_REQUIRED_FIELDS:
		    	AlertDialog.Builder builderCamposObrigatorios = new DefaultAlertDialogBuilder(this, this.getMessage().toString(), false);
		    	builderCamposObrigatorios.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
		    	           public void onClick(DialogInterface _dialog, int id) {
		    	        	   removeDialog(_idDialog);
		    	           }
		    	       })
		    	       ;
		    	return builderCamposObrigatorios.show();
			default:
				return super.onCreateDialog(_idDialog);
		}
	}
	
	/**
	 *
	 * @see android.os.Handler.Callback#handleMessage(android.os.Message)
	 */
	@Override
	public boolean handleMessage(final Message _message) {
		switch(_message.what){
	        case YConstants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE:
	        	this.clearProgressDialogStack();
	        	this.displayDialogWifiAccess();
	        	return true;
	        case YConstants.ERROR_NO_CONNECTIVITY_AVAILABLE:
	        	this.clearProgressDialogStack();
    			this.displayDialogNetworkAccess();
	    		return true;
	        default:
	            return false;
		}
	}
	
	/**
	 * 
	 */
	public final void displayDialogNetworkAccess() {
		this.showDialog(YConstants.ERROR_NO_CONNECTIVITY_AVAILABLE);
	}
	
	/**
	 * 
	 */
	public final void displayDialogWifiAccess() {
		this.showDialog(YConstants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE);
	}
	
	/**
	 * 
	 */
	protected void vibrar() {
		try {
			((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0, 150}, -1);
		} catch (Exception _e) {
			Log.e("YBaseActivity.vibrar", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected int[] getFieldsForWindowsCleanup() {
		return new int[]{};
	}
	
	/**
	 * 
	 */
	public void cleanupWindowUIFields() {
		try {
			YUtilUIFields.clearUIFields(this, this.getFieldsForWindowsCleanup());
		} catch (Exception _e) {
			Log.e("YBaseActivity.limpaTela", _e.getMessage());
		}
	}

	/**
	 * 
	 */
	protected GpsLocationInfo getBestLocalization() {
		GpsLocationInfo gpsLocationInfo = new GpsLocationInfo();
		try {
			this.yBaseLocationListener.updatedGpsLocationInfo();
			this.gpsLocationInfoFinal = new GpsLocationInfo(this.yBaseLocationListener.getGpsLocationInfo());
			if(this.gpsLocationInfoInicial.getLatitude() != 0.0 && this.gpsLocationInfoFinal.getLatitude() != 0.0) {
				if(this.gpsLocationInfoInicial.getAccuracy() != 0.0 &&  this.gpsLocationInfoFinal.getAccuracy() != 0.0) {
					if(this.gpsLocationInfoInicial.getAccuracy() < this.gpsLocationInfoFinal.getAccuracy()) {
						gpsLocationInfo = this.gpsLocationInfoInicial;
					} else {
						gpsLocationInfo = this.gpsLocationInfoFinal;
					}
				} else 
				if (this.gpsLocationInfoInicial.getAccuracy() != 0.0) {
					gpsLocationInfo = this.gpsLocationInfoInicial;
				} else 
				if (this.gpsLocationInfoFinal.getAccuracy() != 0.0) {
					gpsLocationInfo = this.gpsLocationInfoFinal;
				}
			} else 
			if (this.gpsLocationInfoInicial.getLatitude() != 0.0) {
				gpsLocationInfo = this.gpsLocationInfoInicial;
			} else
			if (this.gpsLocationInfoFinal.getLatitude() != 0.0) {
				gpsLocationInfo = this.gpsLocationInfoFinal;
			}
			
		} catch (Exception _e) {
			Log.e("FrmCadastrarVisitaIdentificacao.getMelhorLocalizacao", _e.getMessage());
		}
		return gpsLocationInfo;
	}

	/**
	 * @return the gpsLocationInfoInicial
	 */
	public GpsLocationInfo getGpsLocationInfoInicial() {
		return gpsLocationInfoInicial;
	}

	/**
	 * @param gpsLocationInfoInicial the gpsLocationInfoInicial to set
	 */
	public void setGpsLocationInfoInicial(GpsLocationInfo gpsLocationInfoInicial) {
		this.gpsLocationInfoInicial = gpsLocationInfoInicial;
	}

	/**
	 * @return the gpsLocationInfoFinal
	 */
	public GpsLocationInfo getGpsLocationInfoFinal() {
		return gpsLocationInfoFinal;
	}

	/**
	 * @param gpsLocationInfoFinal the gpsLocationInfoFinal to set
	 */
	public void setGpsLocationInfoFinal(GpsLocationInfo gpsLocationInfoFinal) {
		this.gpsLocationInfoFinal = gpsLocationInfoFinal;
	}

	/**
	 * 
	 * @param _idResourceText
	 * @return
	 */
	protected Dialog buildSimplePositiveAlertDialog(final int _idDialog, final int _idResourceText) {
		AlertDialog.Builder builderSimpleAlertDialog = new DefaultAlertDialogBuilder(this, _idResourceText, false);
		builderSimpleAlertDialog.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface _dialog, int id) {
				YBaseActivity.this.removeDialog(_idDialog);
			}
		})
		;
		return builderSimpleAlertDialog.show();
	}
	
	/**
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Runtime.getRuntime().gc();
		if(super.isFinishing()) {
			
		}
	}
	
	protected  void onFinishing() {
		
	}
	
	
	/**
	 * 
	 * @param _gpsLocationInfo
	 */
	public void onUpdateGpsLocationInfo(GpsLocationInfo _gpsLocationInfo) {
		
	}
}