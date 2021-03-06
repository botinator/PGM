package tc.oc.pgm.regions;

import org.jdom2.Attribute;
import org.jdom2.Element;
import tc.oc.pgm.features.FeatureValidation;
import tc.oc.pgm.map.MapModuleContext;
import tc.oc.pgm.util.MethodParser;
import tc.oc.pgm.util.XMLUtils;
import tc.oc.xml.InvalidXMLException;
import tc.oc.xml.Node;

public class FeatureRegionParser extends RegionParser {

  public FeatureRegionParser(MapModuleContext context) {
    super(context);
  }

  @Override
  public Region parse(Element el) throws InvalidXMLException {
    Region region = this.parseDynamic(el);
    if (region instanceof RegionDefinition) {
      context.features().addFeature(el, (RegionDefinition) region);
    }
    return region;
  }

  @Override
  public Region parseReference(Attribute attr) throws InvalidXMLException {
    return context
        .features()
        .addReference(
            new XMLRegionReference(context.features(), new Node(attr), RegionDefinition.class));
  }

  @MethodParser("region")
  public Region parseRegionTag(Element el) throws InvalidXMLException {
    return this.parseReference(XMLUtils.getRequiredAttribute(el, "id"));
  }

  @Override
  public void validate(Region region, FeatureValidation<RegionDefinition> validation, Node node)
      throws InvalidXMLException {
    if (region instanceof XMLRegionReference) {
      context.features().validate((XMLRegionReference) region, validation);
    } else {
      super.validate(region, validation, node);
    }
  }
}
