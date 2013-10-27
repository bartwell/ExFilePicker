package ru.bartwell.exfilepicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ExFilePickerActivity extends SherlockActivity {

	final private String[] videoExtensions = { "avi", "mp4", "3gp", "mov" };
	final private String[] imagesExtensions = { "jpeg", "jpg", "png", "gif", "bmp", "wbmp" };

	private boolean s_onlyOneItem = false;
	private List<String> s_filterByType;
	private int s_choiceType;

	private MenuItem menuItemView;
	private AbsListView absListView;
	private View emptyView;
	private ArrayList<File> filesList = new ArrayList<File>();
	private ArrayList<String> selected = new ArrayList<String>();
	private File currentDirectory;
	private boolean isMultiChoice = false;
	private ActionMode actionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.efp__main_activity);

		Intent intent = getIntent();
		s_onlyOneItem = intent.getBooleanExtra(ExFilePicker.SET_ONLY_ONE_ITEM, false);
		if (intent.hasExtra(ExFilePicker.SET_FILTER_BY_EXTENSION)) {
			s_filterByType = Arrays.asList(intent.getStringArrayExtra(ExFilePicker.SET_FILTER_BY_EXTENSION));
		}
		s_choiceType = intent.getIntExtra(ExFilePicker.SET_CHOICE_TYPE, ExFilePicker.CHOICE_TYPE_ALL);

		emptyView = getLayoutInflater().inflate(R.layout.efp__empty, null);
		addContentView(emptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		setAbsListView();

		File path = null;
		if (intent.hasExtra(ExFilePicker.SET_START_DIRECTORY)) {
			String startPath = intent.getStringExtra(ExFilePicker.SET_START_DIRECTORY);
			if (startPath != null && startPath.length() > 0) {
				File tmp = new File(startPath);
				if (tmp.exists() && tmp.isDirectory()) path = tmp;
			}
		}
		if (path == null) {
			path = new File("/");
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) path = Environment.getExternalStorageDirectory();
		}
		readDirectory(path);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				if (isMultiChoice) {
					disableMultiChoice();
					if (actionMode != null) actionMode.finish();
				} else {
					File parentFile = currentDirectory.getParentFile();
					if (parentFile == null) {
						complete();
					} else {
						readDirectory(parentFile);
					}
				}
			} else if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getFlags() & KeyEvent.FLAG_LONG_PRESS) == KeyEvent.FLAG_LONG_PRESS) {
				selected.clear();
				complete();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.efp__main, menu);
		menuItemView = menu.findItem(R.id.action_view);
		setMenuItemView();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.action_view) {
			setAbsListView();
			setMenuItemView();
		}
		return super.onOptionsItemSelected(item);
	}

	void disableMultiChoice() {
		isMultiChoice = false;
		selected.clear();
		if (s_choiceType == ExFilePicker.CHOICE_TYPE_FILES && !s_onlyOneItem) {
			readDirectory(currentDirectory);
		}
		((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
	}

	void complete() {
		String path = currentDirectory.getAbsolutePath();
		if(!path.endsWith("/")) path+="/";
		ExFilePickerParcelObject object = new ExFilePickerParcelObject(path, selected, selected.size());
		Intent intent = new Intent();
		intent.putExtra(ExFilePickerParcelObject.class.getCanonicalName(), object);
		setResult(RESULT_OK, intent);
		finish();
	}

	void readDirectory(File path) {
		currentDirectory = path;
		filesList.clear();
		File[] files = path.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (s_choiceType == ExFilePicker.CHOICE_TYPE_DIRECTORIES && !files[i].isDirectory()) continue;
				if (s_filterByType != null && files[i].isFile() && s_filterByType.contains(getFileExtension(files[i].getName()))) continue;
				filesList.add(files[i]);
			}
		}

		Collections.sort(filesList, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				boolean isDirectory1 = file1.isDirectory();
				boolean isDirectory2 = file2.isDirectory();
				if (isDirectory1 && !isDirectory2) return -1;
				if (!isDirectory1 && isDirectory2) return 1;
				return file1.getName().toLowerCase(Locale.getDefault()).compareTo(file2.getName().toLowerCase(Locale.getDefault()));
			}
		});

		((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
	}

	void setMenuItemView() {
		if (absListView.getId() == R.id.gridview) {
			menuItemView.setIcon(R.drawable.efp__ic_action_list);
			menuItemView.setTitle(R.string.action_list);
		} else {
			menuItemView.setIcon(R.drawable.efp__ic_action_grid);
			menuItemView.setTitle(R.string.action_grid);
		}
	}

	void setAbsListView() {
		int curView, nextView;
		if (absListView == null || absListView.getId() == R.id.gridview) {
			curView = R.id.gridview;
			nextView = R.id.listview;
		} else {
			curView = R.id.listview;
			nextView = R.id.gridview;
		}
		absListView = (AbsListView) findViewById(nextView);
		absListView.setEmptyView(emptyView);
		FilesListAdapter adapter = new FilesListAdapter(this, (nextView == R.id.listview) ? R.layout.efp__list_item : R.layout.efp__grid_item);
		if (nextView == R.id.listview) ((ListView) absListView).setAdapter(adapter);
		else ((GridView) absListView).setAdapter(adapter);
		absListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position < filesList.size()) {
					File file = filesList.get(position);
					if (isMultiChoice) {
						CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
						if (checkBox.isChecked()) {
							checkBox.setChecked(false);
							setItemBackground(view, false);
							selected.remove(file.getName());
						} else {
							if (s_onlyOneItem) {
								selected.clear();
								((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
							}
							checkBox.setChecked(true);
							setItemBackground(view, true);
							selected.add(file.getName());
						}
					} else {
						if (file.isDirectory()) {
							readDirectory(file);
							absListView.setSelection(0);
						} else {
							selected.add(file.getName());
							complete();
						}
					}
				}
			}
		});

		if (s_choiceType != ExFilePicker.CHOICE_TYPE_FILES || !s_onlyOneItem) {
			absListView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					if (!isMultiChoice) {
						isMultiChoice = true;
						if (position < filesList.size()) {
							File file = filesList.get(position);
							if (s_choiceType != ExFilePicker.CHOICE_TYPE_FILES || file.isFile()) selected.add(file.getName());
						}

						if (s_choiceType == ExFilePicker.CHOICE_TYPE_FILES && !s_onlyOneItem) {
							ArrayList<File> tmpList = new ArrayList<File>();
							for (int i = 0; i < filesList.size(); i++) {
								File file = filesList.get(i);
								if (file.isFile()) tmpList.add(file);
							}
							filesList = tmpList;
						}

						((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
						actionMode = startActionMode(new ActionMode.Callback() {
							@Override
							public boolean onCreateActionMode(ActionMode mode, Menu menu) {
								getSupportMenuInflater().inflate(R.menu.efp__action_mode, menu);
								return true;
							}

							@Override
							public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
								return false;
							}

							@Override
							public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
								int itemId = item.getItemId();
								if (itemId == R.id.action_select_all) {
									selected.clear();
									for (int i = 0; i < filesList.size(); i++)
										selected.add(filesList.get(i).getName());
								} else if (itemId == R.id.action_deselect) {
									selected.clear();
								} else if (itemId == R.id.action_invert_selection) {
									if (selected.size() > 0) {
										ArrayList<String> tmp = new ArrayList<String>();
										for (int i = 0; i < filesList.size(); i++) {
											String filename = filesList.get(i).getName();
											if (!selected.contains(filename)) tmp.add(filename);
										}
										selected = tmp;
									}
								}
								((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
								return false;
							}

							@Override
							public void onDestroyActionMode(ActionMode mode) {
								if (selected.size() > 0) {
									complete();
								} else {
									disableMultiChoice();
								}
							}
						});
						return true;
					}
					return false;
				}
			});
		}
		findViewById(curView).setVisibility(View.GONE);
		absListView.setVisibility(View.VISIBLE);
	}

	void setItemBackground(View view, boolean state) {
		view.setBackgroundResource(state ? R.drawable.abs__list_activated_holo : 0);
	}

	@SuppressLint("DefaultLocale")
	public String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index == -1) return "";
		return fileName.substring(index + 1, fileName.length()).toLowerCase(Locale.getDefault());
	}

	class FilesListAdapter extends BaseAdapter {
		private Context mContext;
		private int mResource;

		public FilesListAdapter(Context context, int resource) {
			mContext = context;
			mResource = resource;
		}

		@Override
		public int getCount() {
			return filesList.size();
		}

		@Override
		public Object getItem(int position) {
			return filesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			File file = filesList.get(position);

			Bitmap thumbnailBitmap = null;
			ContentResolver crThumb = getContentResolver();
			convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
			ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

			CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			if (selected.contains(file.getName())) {
				checkbox.setChecked(true);
				setItemBackground(convertView, true);
			} else {
				checkbox.setChecked(false);
				setItemBackground(convertView, false);
			}
			if (isMultiChoice) checkbox.setVisibility(View.VISIBLE);

			if (file.isDirectory()) {
				thumbnail.setImageResource(R.drawable.efp__ic_folder);
			} else {
				if (Build.VERSION.SDK_INT >= 5) {
					try {
						if (Arrays.asList(videoExtensions).contains(getFileExtension(file.getName()))) {
							Cursor cursor = crThumb.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Video.Media._ID }, MediaStore.Video.Media.DATA + "='" + file.getAbsolutePath() + "'", null, null);
							if (cursor != null) {
								if (cursor.getCount() > 0) {
									cursor.moveToFirst();
									thumbnailBitmap = MediaStore.Video.Thumbnails.getThumbnail(crThumb, cursor.getInt(0), MediaStore.Video.Thumbnails.MICRO_KIND, null);
								}
								cursor.close();
							}
						} else if (Arrays.asList(imagesExtensions).contains(getFileExtension(file.getName()))) {
							Cursor cursor = crThumb.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "='" + file.getAbsolutePath() + "'", null, null);
							if (cursor != null) {
								if (cursor.getCount() > 0) {
									cursor.moveToFirst();
									thumbnailBitmap = MediaStore.Images.Thumbnails.getThumbnail(crThumb, cursor.getInt(0), MediaStore.Images.Thumbnails.MINI_KIND, null);
								}
								cursor.close();
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
					} catch (Error e) {
						e.printStackTrace();
					}
				}
				if (thumbnailBitmap == null) thumbnail.setImageResource(R.drawable.efp__ic_file);
				else thumbnail.setImageBitmap(thumbnailBitmap);
			}

			TextView filename = (TextView) convertView.findViewById(R.id.filename);
			filename.setText(file.getName());

			TextView filesize = (TextView) convertView.findViewById(R.id.filesize);
			if (filesize != null) {
				if (file.isFile()) filesize.setText(getHumanFileSize(file.length()));
				else filesize.setText("");
			}

			return convertView;
		}

		String getHumanFileSize(long size) {
			String[] units = getResources().getStringArray(R.array.size_units);
			for (int i = units.length - 1; i >= 0; i--) {
				if (size >= Math.pow(1024, i)) {
					return Math.round((size / Math.pow(1024, i))) + " " + units[i];
				}
			}
			return size + " " + units[0];
		}

	}

}
