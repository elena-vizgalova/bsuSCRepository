package by.bsu.flyweighttask.entity;

/**
 *
 * @author Elena Vizgalova
 */
public abstract class AbstractSymbol {
    
    public abstract void draw( SymbolContext context, Point point );
    public abstract void setFont( String fontName, SymbolContext context );
    public abstract String getFont( SymbolContext context );
    public abstract void setSize( int size, SymbolContext context );
    public abstract int getSize( SymbolContext context );
}
