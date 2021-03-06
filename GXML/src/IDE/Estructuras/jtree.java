/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.Estructuras;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author fernando
 */
public class jtree implements TreeExpansionListener {

    private JTree jTree1;
    private DefaultTreeModel modelo;

    public DefaultTreeModel getModelo() {
        return modelo;
    }

    public jtree() {
    }

    public jtree(JTree jTree1) {
        this.jTree1 = jTree1;
    }

    public void setJTree(JTree jTree1) {
        this.jTree1 = jTree1;
    }

    public void init() throws IOException {
        //"/home/fernando/NetBeansProjects/GXMLProyecto/GenericXML/GXML/Entradas_Script"
        File archivo = new File(IDE.IDE_Ventana.JTREE_DIR_ROOT);
        //System.out.println("llego aqui2");
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(IDE.IDE_Ventana.JTREE_DIR_ROOT);
        //System.out.println("llego aqui3");
        modelo = new DefaultTreeModel(top);
        
        ImageIcon imageIcon = new ImageIcon("Icons/file.png");
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(imageIcon);
        jTree1.setCellRenderer(renderer);
        //System.out.println("llego aqui4");

        jTree1.setModel(modelo);
        jTree1.addTreeExpansionListener(this);
        //System.out.println("llego aqui5");
        for (File f : archivo.listFiles()) {
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(f);
            //System.out.println("Llego aqui 6");
            top.add(raiz);
            actualizaNodo(raiz, f);
            //System.out.println("llego aqui7");
        }
    }

    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f) {
        nodo.removeAllChildren();
        return actualizaNodo(nodo, f, 2);
    }

    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f, int profundidad) {
        File[] files = f.listFiles(); // de el nodo que llega listamos todos sus archivos
        if (files != null && profundidad > 0) {
            for (File file : files) {
                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(file);
                actualizaNodo(nuevo, file, profundidad - 1);
                nodo.add(nuevo); //crea el nodo
            }
        }
        return true;
    }

    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        TreePath path = event.getPath(); // Se obtiene el path del nodo
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node == null || !(node.getUserObject() instanceof File)) {
            return;
        }
        File f = (File) node.getUserObject();
        actualizaNodo(node, f);  //actualiza la estructura
        JTree tree = (JTree) event.getSource();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.nodeStructureChanged(node);
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {

    }

}
