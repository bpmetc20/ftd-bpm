package org.activiti.designer.handlers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.awt.Window;
import java.io.File;
import java.util.ArrayList;

import org.activiti.designer.eclipse.common.ActivitiPlugin;
import org.activiti.designer.eclipse.editor.ActivitiDiagramEditorInput;
import org.activiti.designer.util.ActivitiConstants;
import org.activiti.designer.util.DiagramHandler;
import org.activiti.designer.util.editor.BpmnMemoryModel;
import org.activiti.designer.util.editor.ModelHandler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;

public class LoadProcessHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		String[] tasksArray = {"FTDGeneric", "FTDProposal", "FTDStartup", "FTNew", "FTDAssigner", "FTDKickOff", "FTDClientProposal"};
		String result = selectProcess(window, tasksArray);		
		
		
		//String result = MyFileDialog.openDialog(window.getShell());
		if (!result.isEmpty()) {
			String diagramFullPath = DiagramHandler.processesFolder + result + ".bpmn";
			File file  = new File(diagramFullPath);
			IPath location= Path.fromOSString(file.getAbsolutePath()); 
			IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(location);
			DiagramHandler.openDiagramForBpmnFile(ifile);
		}		
		return window;		
	}
	
	public String selectProcess(IWorkbenchWindow window, String[] elements) {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new LabelProvider());

        dialog.setTitle("Select Process");

        dialog.setMessage("&Select a process from the list. Type word using filter");

        dialog.setElements(elements);

        String selected = "";
        
        dialog.setMultipleSelection(false);
        int returnCode = dialog.open();
        if (returnCode == 0) {
            selected = (String) dialog.getFirstResult();
        }
        return selected;
	}
}
