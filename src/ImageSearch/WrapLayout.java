/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageSearch;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author raj
 */
public class WrapLayout extends FlowLayout {

    private Integer maxW = null;
    private Dimension preferredLayoutSize;

//    public WrapLayout() {
//        super();
//    }
//
//    public WrapLayout(int align) {
//        super(align);
//    }

   
    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

//    public WrapLayout(Integer width) {
//        super();
//        this.maxW = width;
//    }
//
//    public WrapLayout(int align, Integer width) {
//        super(align);
//        this.maxW = width;
//    }
//
//    public WrapLayout(int align, int hgap, int vgap, Integer width) {
//        super(align, hgap, vgap);
//        this.maxW = width;
//    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

//    @Override
//    public Dimension minimumLayoutSize(Container target) {
//        Dimension minimum = layoutSize(target, false);
//        minimum.width -= (getHgap() + 1);
//        return minimum;
//    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {

            int targetWidth = maxW != null && maxW > 0 ? maxW : target.getSize().width;

            if (targetWidth == 0) {
                targetWidth = Integer.MAX_VALUE;
            }

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            //  Fit components into the allowed width
            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);

                if (m.isVisible()) {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    //  Can't add the component to current row. Start a new row.
                    if (rowWidth + d.width > maxWidth) {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    //  Add a horizontal gap for all components after the first
                    if (rowWidth != 0) {
                        rowWidth += hgap;
                    }

                    rowWidth += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            addRow(dim, rowWidth, rowHeight);

            dim.width += horizontalInsetsAndGap;
            dim.height += insets.top + insets.bottom + vgap * 2;

            Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);

            if (scrollPane != null && target.isValid()) {
                dim.width -= (hgap + 1);
            }

            return dim;
        }
    }

    private void addRow(Dimension dim, int rowWidth, int rowHeight) {
        dim.width = Math.max(dim.width, rowWidth);

        if (dim.height > 0) {
            dim.height += getVgap();
        }

        dim.height += rowHeight;
    }

}
