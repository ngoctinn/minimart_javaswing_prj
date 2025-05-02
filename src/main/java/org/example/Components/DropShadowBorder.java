package org.example.Components;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * A border that draws a drop shadow behind a component.
 * Based on the design requirements of adding subtle shadows with opacity.
 */
public class DropShadowBorder extends AbstractBorder {
    private Color shadowColor;
    private int shadowSize;
    private int shadowOffset;
    private int shadowOpacity;
    private boolean showTopShadow;
    private boolean showLeftShadow;
    private boolean showBottomShadow;
    private boolean showRightShadow;

    public DropShadowBorder() {
        this(Color.BLACK, 5, 5, 0, true, true, true, true);
    }

    public DropShadowBorder(Color shadowColor, int shadowSize, int shadowOffset, int shadowOpacity,
                           boolean showTopShadow, boolean showLeftShadow, boolean showBottomShadow, boolean showRightShadow) {
        this.shadowColor = shadowColor;
        this.shadowSize = shadowSize;
        this.shadowOffset = shadowOffset;
        this.shadowOpacity = shadowOpacity;
        this.showTopShadow = showTopShadow;
        this.showLeftShadow = showLeftShadow;
        this.showBottomShadow = showBottomShadow;
        this.showRightShadow = showRightShadow;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Calculate the total shadow area
        int topShadow = showTopShadow ? shadowSize : 0;
        int leftShadow = showLeftShadow ? shadowSize : 0;
        int bottomShadow = showBottomShadow ? shadowSize + shadowOffset : 0;
        int rightShadow = showRightShadow ? shadowSize + shadowOffset : 0;
        
        // Draw shadow layers
        for (int i = 0; i < shadowSize; i++) {
            float ratio = (float) i / (float) shadowSize;
            
            // Calculate transparency for this layer
            int alpha = (int) (shadowColor.getAlpha() * (1 - ratio * 0.7f));
            Color color = new Color(
                    shadowColor.getRed(),
                    shadowColor.getGreen(),
                    shadowColor.getBlue(),
                    alpha);
            
            g2.setColor(color);
            
            // Draw appropriate shadow sides
            if (showBottomShadow) {
                g2.fillRect(
                    x + leftShadow - i,
                    y + height - bottomShadow + i + shadowOffset,
                    width - leftShadow - rightShadow + i * 2,
                    i
                );
            }
            
            if (showRightShadow) {
                g2.fillRect(
                    x + width - rightShadow + i + shadowOffset,
                    y + topShadow - i,
                    i,
                    height - topShadow - bottomShadow + i * 2
                );
            }
            
            if (showLeftShadow) {
                g2.fillRect(
                    x,
                    y + topShadow - i,
                    i,
                    height - topShadow - bottomShadow + i * 2
                );
            }
            
            if (showTopShadow) {
                g2.fillRect(
                    x + leftShadow - i,
                    y,
                    width - leftShadow - rightShadow + i * 2,
                    i
                );
            }
        }
        
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(
            showTopShadow ? shadowSize : 0,
            showLeftShadow ? shadowSize : 0,
            showBottomShadow ? shadowSize + shadowOffset : 0,
            showRightShadow ? shadowSize + shadowOffset : 0
        );
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = showTopShadow ? shadowSize : 0;
        insets.left = showLeftShadow ? shadowSize : 0;
        insets.bottom = showBottomShadow ? shadowSize + shadowOffset : 0;
        insets.right = showRightShadow ? shadowSize + shadowOffset : 0;
        return insets;
    }
} 