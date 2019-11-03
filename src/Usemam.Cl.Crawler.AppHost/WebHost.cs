using Funq;
using ServiceStack;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace Usemam.Cl.Crawler.AppHost
{
    public class WebHost : AppHostBase
    {
        private readonly IEnumerable<IAppHostExtension> _extensions;

        public WebHost(
            string hostName,
            IEnumerable<Assembly> assemblies,
            IEnumerable<IAppHostExtension> extensions)
            : base(hostName, assemblies.ToArray())
        {
            _extensions = extensions;
        }

        public override void Configure(Container container)
        {
            foreach (IAppHostExtension appHostExtension in _extensions)
            {
                appHostExtension.Attach(container);
            }
        }
    }
}
