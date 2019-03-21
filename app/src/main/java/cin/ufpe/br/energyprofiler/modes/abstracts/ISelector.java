package cin.ufpe.br.energyprofiler.modes.abstracts;

import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;

/**
 * Created by welli on 04-Dec-17.
 */

public interface ISelector {

    public void selectAction(IActions actionParam) throws ActionNotImplementedException;
    public EnumCollections.Type getCollectionsType();
}
