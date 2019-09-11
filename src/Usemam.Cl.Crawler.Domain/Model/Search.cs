using System;
using System.Collections.Generic;
using System.Text;

namespace Usemam.Cl.Crawler.Domain.Model
{
    public class Search
    {
        public Search()
        {
            Ads = new List<Ad>();
        }

        public string Url { get; set; }

        public DateTime Created { get; set; }

        public bool IsActive { get; set; }

        public IList<Ad> Ads { get; set; }
    }
}
