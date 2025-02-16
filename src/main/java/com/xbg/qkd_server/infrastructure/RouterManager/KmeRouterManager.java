package com.xbg.qkd_server.infrastructure.RouterManager;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2025-01-29 22:55
 */
public interface KmeRouterManager {

    List<SecurityNode> getAllSecurityNodes();

    List<SAENode> getSAENodes();

    List<KMENode> getKMENodes();

    Optional<SAENode> querySAEBySAEId(String saeId);

    Optional<KMENode> queryKMEByKMEId(String saeId);

    SAENode getCurrentSAEId();

    KMENode getCurrentKME();

    Boolean updateSAEHostInfo(String saeId, Host host);
}
