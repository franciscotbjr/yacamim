/**
 * BaseActivity.java
 * Copyright 2012 yacamim.org.br
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import br.org.yacamim.util.Constants;
import br.org.yacamim.util.UtilUIFields;
import br.org.yacamim.xml.DefaultDataServiceHandler;

/**
 * 
 * Class BaseActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseActivity extends Activity implements Callback {
	
	private BaseLocationListener baseLocationListener;
	
	private LocationManager locationManager;
	
	private GpsLocationInfo gpsLocationInfoInicial = new GpsLocationInfo();
	
	private GpsLocationInfo gpsLocationInfoFinal = new GpsLocationInfo();
	
	private StringBuilder message;
	
	private List<ProgressDialog> progressDialogStack = new ArrayList<ProgressDialog>();
	
	/**
	 * 
	 */
	public BaseActivity() {
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
		this.loadServiceURLs();
		this.loadClassMapping();
		this.loadParams();
	}

	/**
	 * 
	 */
	protected void initGPS() {
		try {
			this.gpsLocationInfoInicial = new GpsLocationInfo();
			this.gpsLocationInfoFinal = new GpsLocationInfo();
			this.locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			this.baseLocationListener = new BaseLocationListener(this);
			this.locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, baseLocationListener);
		} catch (Exception _e) {
			Log.e("BaseActivity.initGPS", _e.getMessage());
		}
	}

    /**
     * 
     */
	private void keepScreenOn() {
		try {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} catch (Exception _e) {
			Log.e("BaseActivity.keepScreenOn", _e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	private void loadServiceURLs() {
		try {
			if(!YacamimState.getInstance().isServiceUrlsLoaded()) {
				DefaultDataServiceHandler.loadURLsServices(this);
			}
		} catch (Exception _e) {
			Log.e("BaseActivity.loadServiceURLs", _e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	private void loadClassMapping() {
		try {
			if(!YacamimState.getInstance().isClassMappingLoaded()) {
				DefaultDataServiceHandler.loadClassMapping(this);
			}
		} catch (Exception _e) {
			Log.e("BaseActivity.loadClassMapping", _e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	private void loadParams() {
		try {
			if(!YacamimState.getInstance().isParamsLoaded()) {
				DefaultDataServiceHandler.loadParams(this);
			}
		} catch (Exception _e) {
			Log.e("BaseActivity.loadParams", _e.getMessage());
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
			Log.e("BaseActivity.buildMessage", _e.getMessage());
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
			Log.e("BaseActivity._uiFieldNames", _e.getMessage());
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
			Log.e("BaseActivity.displayProgressDialog", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _mensagem
	 */
	public void displayProgressDialog() {
		try {
			this.progressDialogStack.add(ProgressDialog.show(this, "", this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgWait()), true, false));
		} catch (Exception _e) {
			Log.e("BaseActivity.displayProgressDialog", _e.getMessage());
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
			Log.e("BaseActivity.removeProgressDialog", _e.getMessage());
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
			Log.e("BaseActivity.clearProgressDialogStack", _e.getMessage());
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
				case Constants.ERROR_NO_INFORMATION_FOUND:
					this.showDialogNoInformationFound();
					break;
				case Constants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS:
					this.showDialogNoRecordFound();
					break;
			}
		} catch (Exception _e) {
			Log.e("BaseActivity.onActivityResult", _e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	protected void showDialogNoInformationFound() {
		showDialog(Constants.ERROR_NO_INFORMATION_FOUND);
	}
	
	/**
	 * 
	 */
	protected void finishForNoInformationFound() {
		super.setResult(Constants.ERROR_NO_INFORMATION_FOUND);
		super.finish();
	}
	
	/**
	 * 
	 */
	protected void showDialogNoRecordFound() {
		showDialog(Constants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS);
	}
	
	/**
	 * 
	 */
	protected void showDialogNoRecordSelected() {
		showDialog(Constants.ERROR_NO_RECORD_SELECTED);
	}
	
	/**
	 * 
	 */
	protected void finishForNoRecordFound() {
		super.setResult(Constants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS);
		super.finish();
	}
	
	/**
	 * 
	 */
	protected void finishForNoRecordSelected() {
		super.setResult(Constants.ERROR_NO_RECORD_SELECTED);
		super.finish();
	}

	/**
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(final int _idDialog) {
		switch(_idDialog) {
			case Constants.ERROR_NO_INFORMATION_FOUND:
				AlertDialog.Builder builderNenhumResultadoEncontrado = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgNoInformationFound()).toString(), false);
	        	builderNenhumResultadoEncontrado.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
	        	           public void onClick(DialogInterface _dialog, int id) {
	        	        	   removeDialog(_idDialog);
	        	           }
	        	       });
	        	return builderNenhumResultadoEncontrado.show();
			case Constants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS:
	        	AlertDialog.Builder builderNenhumRegistro = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgNoRecordsFoundForParameters()).toString(), false);
	        	builderNenhumRegistro.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
	        	           public void onClick(DialogInterface _dialog, int id) {
	        	        	   removeDialog(_idDialog);
	        	           }
	        	       });
	        	return builderNenhumRegistro.show();
			case Constants.ERROR_NO_RECORD_SELECTED:
				AlertDialog.Builder builderNenhumRegistroSelecionado = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgNoRecordSelected()).toString(), false);
				builderNenhumRegistroSelecionado.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(_idDialog);
					}
				});
				return builderNenhumRegistroSelecionado.show();
			case Constants.ERROR_NO_CONNECTIVITY_AVAILABLE:
	        	AlertDialog.Builder builderSemAcessoRede = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgNoConnectivityAvailable()).toString(), false);
	        	builderSemAcessoRede.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
	        	           public void onClick(DialogInterface _dialog, int id) {
	        	        	   removeDialog(_idDialog);
	        	           }
	        	       });
	        	return builderSemAcessoRede.show();
			case Constants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE:
				AlertDialog.Builder builderSemAcessoWifi = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgNoWifiConnectivityAvailable()).toString(), false);
				builderSemAcessoWifi.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(_idDialog);
					}
				});
				return builderSemAcessoWifi.show();
			case Constants.INFO_DATA_SUCCESFULLY_INSERTED:
		    	AlertDialog.Builder builderDadosCadastradosSucesso = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgSuccesfullyInserted()), false);
		    	builderDadosCadastradosSucesso.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface _dialog, int id) {
		    			removeDialog(_idDialog);
		    		}
		    	})
		    	;
		    	return builderDadosCadastradosSucesso.show();
			case Constants.INFO_DATA_SUCCESFULLY_UPDATED:
				AlertDialog.Builder builderDadosAlteradosSucesso = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgSuccesfullyUpdated()), false);
				builderDadosAlteradosSucesso.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(_idDialog);
					}
				})
				;
				return builderDadosAlteradosSucesso.show();
			case Constants.ERROR_INVALID_DATA:
		    	AlertDialog.Builder builderDadosInvalidos = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgInvalidData()), false);
		    	builderDadosInvalidos.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface _dialog, int id) {
		    			removeDialog(_idDialog);
		    		}
		    	})
		    	;
		    	return builderDadosInvalidos.show();
		    case Constants.ERROR_REQUIRED_FIELDS:
		    	AlertDialog.Builder builderCamposObrigatorios = new DefaultAlertDialogBuilder(this, this.getMessage().toString(), false);
		    	builderCamposObrigatorios.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
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
	        case Constants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE:
	        	this.clearProgressDialogStack();
	        	this.displayDialogWifiAccess();
	        	return true;
	        case Constants.ERROR_NO_CONNECTIVITY_AVAILABLE:
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
		this.showDialog(Constants.ERROR_NO_CONNECTIVITY_AVAILABLE);
	}
	
	/**
	 * 
	 */
	public final void displayDialogWifiAccess() {
		this.showDialog(Constants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE);
	}
	
	/**
	 * 
	 */
	protected void vibrar() {
		try {
			((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0, 150}, -1);
		} catch (Exception _e) {
			Log.e("BaseActivity.vibrar", _e.getMessage());
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
			UtilUIFields.clearUIFields(this, this.getFieldsForWindowsCleanup());
		} catch (Exception _e) {
			Log.e("BaseActivity.limpaTela", _e.getMessage());
		}
	}

	/**
	 * 
	 */
	protected GpsLocationInfo getBestLocalization() {
		GpsLocationInfo gpsLocationInfo = new GpsLocationInfo();
		try {
			this.baseLocationListener.updatedGpsLocationInfo();
			this.gpsLocationInfoFinal = new GpsLocationInfo(this.baseLocationListener.getGpsLocationInfo());
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
		builderSimpleAlertDialog.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface _dialog, int id) {
				BaseActivity.this.removeDialog(_idDialog);
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