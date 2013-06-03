package by.bsu.flyweighttask.entity;

/**
 *
 * @author Elena Vizgalova
 */
public class Symbol extends AbstractSymbol {

    private char character;
    
    public Symbol( char character ) {
        this.character = character;
    }
    
    @Override
    public void draw( SymbolContext context, Point point ) {
        System.out.println( "Character: " + character + " on x = " + point.getxCoordinate() 
                + ", y = " + point.getyCoordinate() + "; font name = " + context.getFontName()
                + ", font size = " + context.getSize() );
    }

    @Override
    public void setFont( String fontName, SymbolContext context ) {
        context.setFontName( fontName );
    }

    @Override
    public String getFont( SymbolContext context ) {
        return context.getFontName();
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }
    
    @Override
    public void setSize( int size, SymbolContext context ) {
        context.setSize( size );
    }

    @Override
    public int getSize( SymbolContext context ) {
        return context.getSize();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.character;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Symbol other = (Symbol) obj;
        if (this.character != other.character) {
            return false;
        }
        return true;
    }

}