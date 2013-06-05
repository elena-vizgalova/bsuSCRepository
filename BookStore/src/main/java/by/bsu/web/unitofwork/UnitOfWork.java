package by.bsu.web.unitofwork;

/**
 *
 * @author Elena Vizgalova
 */
public interface UnitOfWork {
    
    public abstract void commit();
    
    public abstract void registerToRemove( Object newObject );
    public abstract void registerNew( Object newObject );
    public abstract void registerDirty( Object newObject );
    public abstract boolean isInNew( Object object );
    public abstract boolean isInDirty( Object object );
    public abstract boolean isInRemove( Object object );
    
}
