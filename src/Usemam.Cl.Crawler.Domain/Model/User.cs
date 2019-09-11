using System.Collections.Generic;

namespace Usemam.Cl.Crawler.Domain.Model
{
    public class User
    {
        public User()
        {
            Searches = new List<Search>();
        }

        public string Email { get; set; }

        public IList<Search> Searches { get; set; }
    }
}